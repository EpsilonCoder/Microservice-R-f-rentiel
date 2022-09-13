import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypeAutoriteContractanteDetailComponent } from './type-autorite-contractante-detail.component';

describe('TypeAutoriteContractante Management Detail Component', () => {
  let comp: TypeAutoriteContractanteDetailComponent;
  let fixture: ComponentFixture<TypeAutoriteContractanteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TypeAutoriteContractanteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ typeAutoriteContractante: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TypeAutoriteContractanteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TypeAutoriteContractanteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load typeAutoriteContractante on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.typeAutoriteContractante).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
