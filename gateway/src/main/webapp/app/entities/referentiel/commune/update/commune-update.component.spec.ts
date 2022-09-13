import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommuneFormService } from './commune-form.service';
import { CommuneService } from '../service/commune.service';
import { ICommune } from '../commune.model';
import { ITypeAutoriteContractante } from 'app/entities/referentiel/type-autorite-contractante/type-autorite-contractante.model';
import { TypeAutoriteContractanteService } from 'app/entities/referentiel/type-autorite-contractante/service/type-autorite-contractante.service';
import { IDepartement } from 'app/entities/referentiel/departement/departement.model';
import { DepartementService } from 'app/entities/referentiel/departement/service/departement.service';

import { CommuneUpdateComponent } from './commune-update.component';

describe('Commune Management Update Component', () => {
  let comp: CommuneUpdateComponent;
  let fixture: ComponentFixture<CommuneUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let communeFormService: CommuneFormService;
  let communeService: CommuneService;
  let typeAutoriteContractanteService: TypeAutoriteContractanteService;
  let departementService: DepartementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommuneUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CommuneUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommuneUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    communeFormService = TestBed.inject(CommuneFormService);
    communeService = TestBed.inject(CommuneService);
    typeAutoriteContractanteService = TestBed.inject(TypeAutoriteContractanteService);
    departementService = TestBed.inject(DepartementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TypeAutoriteContractante query and add missing value', () => {
      const commune: ICommune = { id: 456 };
      const typeautorite: ITypeAutoriteContractante = { id: 1581 };
      commune.typeautorite = typeautorite;

      const typeAutoriteContractanteCollection: ITypeAutoriteContractante[] = [{ id: 32380 }];
      jest
        .spyOn(typeAutoriteContractanteService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: typeAutoriteContractanteCollection })));
      const additionalTypeAutoriteContractantes = [typeautorite];
      const expectedCollection: ITypeAutoriteContractante[] = [
        ...additionalTypeAutoriteContractantes,
        ...typeAutoriteContractanteCollection,
      ];
      jest.spyOn(typeAutoriteContractanteService, 'addTypeAutoriteContractanteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commune });
      comp.ngOnInit();

      expect(typeAutoriteContractanteService.query).toHaveBeenCalled();
      expect(typeAutoriteContractanteService.addTypeAutoriteContractanteToCollectionIfMissing).toHaveBeenCalledWith(
        typeAutoriteContractanteCollection,
        ...additionalTypeAutoriteContractantes.map(expect.objectContaining)
      );
      expect(comp.typeAutoriteContractantesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Departement query and add missing value', () => {
      const commune: ICommune = { id: 456 };
      const department: IDepartement = { id: 99092 };
      commune.department = department;

      const departementCollection: IDepartement[] = [{ id: 88413 }];
      jest.spyOn(departementService, 'query').mockReturnValue(of(new HttpResponse({ body: departementCollection })));
      const additionalDepartements = [department];
      const expectedCollection: IDepartement[] = [...additionalDepartements, ...departementCollection];
      jest.spyOn(departementService, 'addDepartementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commune });
      comp.ngOnInit();

      expect(departementService.query).toHaveBeenCalled();
      expect(departementService.addDepartementToCollectionIfMissing).toHaveBeenCalledWith(
        departementCollection,
        ...additionalDepartements.map(expect.objectContaining)
      );
      expect(comp.departementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const commune: ICommune = { id: 456 };
      const typeautorite: ITypeAutoriteContractante = { id: 29959 };
      commune.typeautorite = typeautorite;
      const department: IDepartement = { id: 55711 };
      commune.department = department;

      activatedRoute.data = of({ commune });
      comp.ngOnInit();

      expect(comp.typeAutoriteContractantesSharedCollection).toContain(typeautorite);
      expect(comp.departementsSharedCollection).toContain(department);
      expect(comp.commune).toEqual(commune);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommune>>();
      const commune = { id: 123 };
      jest.spyOn(communeFormService, 'getCommune').mockReturnValue(commune);
      jest.spyOn(communeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commune });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commune }));
      saveSubject.complete();

      // THEN
      expect(communeFormService.getCommune).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(communeService.update).toHaveBeenCalledWith(expect.objectContaining(commune));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommune>>();
      const commune = { id: 123 };
      jest.spyOn(communeFormService, 'getCommune').mockReturnValue({ id: null });
      jest.spyOn(communeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commune: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commune }));
      saveSubject.complete();

      // THEN
      expect(communeFormService.getCommune).toHaveBeenCalled();
      expect(communeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommune>>();
      const commune = { id: 123 };
      jest.spyOn(communeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commune });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(communeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTypeAutoriteContractante', () => {
      it('Should forward to typeAutoriteContractanteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(typeAutoriteContractanteService, 'compareTypeAutoriteContractante');
        comp.compareTypeAutoriteContractante(entity, entity2);
        expect(typeAutoriteContractanteService.compareTypeAutoriteContractante).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDepartement', () => {
      it('Should forward to departementService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(departementService, 'compareDepartement');
        comp.compareDepartement(entity, entity2);
        expect(departementService.compareDepartement).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
