import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TypeAutoriteContractanteService } from '../service/type-autorite-contractante.service';

import { TypeAutoriteContractanteComponent } from './type-autorite-contractante.component';

describe('TypeAutoriteContractante Management Component', () => {
  let comp: TypeAutoriteContractanteComponent;
  let fixture: ComponentFixture<TypeAutoriteContractanteComponent>;
  let service: TypeAutoriteContractanteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'type-autorite-contractante', component: TypeAutoriteContractanteComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [TypeAutoriteContractanteComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(TypeAutoriteContractanteComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypeAutoriteContractanteComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TypeAutoriteContractanteService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.typeAutoriteContractantes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to typeAutoriteContractanteService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getTypeAutoriteContractanteIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getTypeAutoriteContractanteIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
