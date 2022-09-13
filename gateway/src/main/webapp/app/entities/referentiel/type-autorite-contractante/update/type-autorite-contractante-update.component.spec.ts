import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TypeAutoriteContractanteFormService } from './type-autorite-contractante-form.service';
import { TypeAutoriteContractanteService } from '../service/type-autorite-contractante.service';
import { ITypeAutoriteContractante } from '../type-autorite-contractante.model';

import { TypeAutoriteContractanteUpdateComponent } from './type-autorite-contractante-update.component';

describe('TypeAutoriteContractante Management Update Component', () => {
  let comp: TypeAutoriteContractanteUpdateComponent;
  let fixture: ComponentFixture<TypeAutoriteContractanteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typeAutoriteContractanteFormService: TypeAutoriteContractanteFormService;
  let typeAutoriteContractanteService: TypeAutoriteContractanteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TypeAutoriteContractanteUpdateComponent],
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
      .overrideTemplate(TypeAutoriteContractanteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypeAutoriteContractanteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typeAutoriteContractanteFormService = TestBed.inject(TypeAutoriteContractanteFormService);
    typeAutoriteContractanteService = TestBed.inject(TypeAutoriteContractanteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const typeAutoriteContractante: ITypeAutoriteContractante = { id: 456 };

      activatedRoute.data = of({ typeAutoriteContractante });
      comp.ngOnInit();

      expect(comp.typeAutoriteContractante).toEqual(typeAutoriteContractante);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypeAutoriteContractante>>();
      const typeAutoriteContractante = { id: 123 };
      jest.spyOn(typeAutoriteContractanteFormService, 'getTypeAutoriteContractante').mockReturnValue(typeAutoriteContractante);
      jest.spyOn(typeAutoriteContractanteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeAutoriteContractante });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeAutoriteContractante }));
      saveSubject.complete();

      // THEN
      expect(typeAutoriteContractanteFormService.getTypeAutoriteContractante).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(typeAutoriteContractanteService.update).toHaveBeenCalledWith(expect.objectContaining(typeAutoriteContractante));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypeAutoriteContractante>>();
      const typeAutoriteContractante = { id: 123 };
      jest.spyOn(typeAutoriteContractanteFormService, 'getTypeAutoriteContractante').mockReturnValue({ id: null });
      jest.spyOn(typeAutoriteContractanteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeAutoriteContractante: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeAutoriteContractante }));
      saveSubject.complete();

      // THEN
      expect(typeAutoriteContractanteFormService.getTypeAutoriteContractante).toHaveBeenCalled();
      expect(typeAutoriteContractanteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypeAutoriteContractante>>();
      const typeAutoriteContractante = { id: 123 };
      jest.spyOn(typeAutoriteContractanteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeAutoriteContractante });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typeAutoriteContractanteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
