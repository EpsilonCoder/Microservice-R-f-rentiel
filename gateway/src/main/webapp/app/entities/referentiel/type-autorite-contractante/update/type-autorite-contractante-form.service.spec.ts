import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../type-autorite-contractante.test-samples';

import { TypeAutoriteContractanteFormService } from './type-autorite-contractante-form.service';

describe('TypeAutoriteContractante Form Service', () => {
  let service: TypeAutoriteContractanteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeAutoriteContractanteFormService);
  });

  describe('Service methods', () => {
    describe('createTypeAutoriteContractanteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTypeAutoriteContractanteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
            libelle: expect.any(Object),
            ordre: expect.any(Object),
          })
        );
      });

      it('passing ITypeAutoriteContractante should create a new form with FormGroup', () => {
        const formGroup = service.createTypeAutoriteContractanteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
            libelle: expect.any(Object),
            ordre: expect.any(Object),
          })
        );
      });
    });

    describe('getTypeAutoriteContractante', () => {
      it('should return NewTypeAutoriteContractante for default TypeAutoriteContractante initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTypeAutoriteContractanteFormGroup(sampleWithNewData);

        const typeAutoriteContractante = service.getTypeAutoriteContractante(formGroup) as any;

        expect(typeAutoriteContractante).toMatchObject(sampleWithNewData);
      });

      it('should return NewTypeAutoriteContractante for empty TypeAutoriteContractante initial value', () => {
        const formGroup = service.createTypeAutoriteContractanteFormGroup();

        const typeAutoriteContractante = service.getTypeAutoriteContractante(formGroup) as any;

        expect(typeAutoriteContractante).toMatchObject({});
      });

      it('should return ITypeAutoriteContractante', () => {
        const formGroup = service.createTypeAutoriteContractanteFormGroup(sampleWithRequiredData);

        const typeAutoriteContractante = service.getTypeAutoriteContractante(formGroup) as any;

        expect(typeAutoriteContractante).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITypeAutoriteContractante should not enable id FormControl', () => {
        const formGroup = service.createTypeAutoriteContractanteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTypeAutoriteContractante should disable id FormControl', () => {
        const formGroup = service.createTypeAutoriteContractanteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
