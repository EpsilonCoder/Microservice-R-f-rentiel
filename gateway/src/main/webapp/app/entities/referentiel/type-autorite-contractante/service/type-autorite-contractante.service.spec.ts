import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITypeAutoriteContractante } from '../type-autorite-contractante.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../type-autorite-contractante.test-samples';

import { TypeAutoriteContractanteService } from './type-autorite-contractante.service';

const requireRestSample: ITypeAutoriteContractante = {
  ...sampleWithRequiredData,
};

describe('TypeAutoriteContractante Service', () => {
  let service: TypeAutoriteContractanteService;
  let httpMock: HttpTestingController;
  let expectedResult: ITypeAutoriteContractante | ITypeAutoriteContractante[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TypeAutoriteContractanteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a TypeAutoriteContractante', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const typeAutoriteContractante = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(typeAutoriteContractante).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TypeAutoriteContractante', () => {
      const typeAutoriteContractante = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(typeAutoriteContractante).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TypeAutoriteContractante', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TypeAutoriteContractante', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TypeAutoriteContractante', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTypeAutoriteContractanteToCollectionIfMissing', () => {
      it('should add a TypeAutoriteContractante to an empty array', () => {
        const typeAutoriteContractante: ITypeAutoriteContractante = sampleWithRequiredData;
        expectedResult = service.addTypeAutoriteContractanteToCollectionIfMissing([], typeAutoriteContractante);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typeAutoriteContractante);
      });

      it('should not add a TypeAutoriteContractante to an array that contains it', () => {
        const typeAutoriteContractante: ITypeAutoriteContractante = sampleWithRequiredData;
        const typeAutoriteContractanteCollection: ITypeAutoriteContractante[] = [
          {
            ...typeAutoriteContractante,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTypeAutoriteContractanteToCollectionIfMissing(
          typeAutoriteContractanteCollection,
          typeAutoriteContractante
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TypeAutoriteContractante to an array that doesn't contain it", () => {
        const typeAutoriteContractante: ITypeAutoriteContractante = sampleWithRequiredData;
        const typeAutoriteContractanteCollection: ITypeAutoriteContractante[] = [sampleWithPartialData];
        expectedResult = service.addTypeAutoriteContractanteToCollectionIfMissing(
          typeAutoriteContractanteCollection,
          typeAutoriteContractante
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typeAutoriteContractante);
      });

      it('should add only unique TypeAutoriteContractante to an array', () => {
        const typeAutoriteContractanteArray: ITypeAutoriteContractante[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const typeAutoriteContractanteCollection: ITypeAutoriteContractante[] = [sampleWithRequiredData];
        expectedResult = service.addTypeAutoriteContractanteToCollectionIfMissing(
          typeAutoriteContractanteCollection,
          ...typeAutoriteContractanteArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const typeAutoriteContractante: ITypeAutoriteContractante = sampleWithRequiredData;
        const typeAutoriteContractante2: ITypeAutoriteContractante = sampleWithPartialData;
        expectedResult = service.addTypeAutoriteContractanteToCollectionIfMissing([], typeAutoriteContractante, typeAutoriteContractante2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typeAutoriteContractante);
        expect(expectedResult).toContain(typeAutoriteContractante2);
      });

      it('should accept null and undefined values', () => {
        const typeAutoriteContractante: ITypeAutoriteContractante = sampleWithRequiredData;
        expectedResult = service.addTypeAutoriteContractanteToCollectionIfMissing([], null, typeAutoriteContractante, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typeAutoriteContractante);
      });

      it('should return initial array if no TypeAutoriteContractante is added', () => {
        const typeAutoriteContractanteCollection: ITypeAutoriteContractante[] = [sampleWithRequiredData];
        expectedResult = service.addTypeAutoriteContractanteToCollectionIfMissing(typeAutoriteContractanteCollection, undefined, null);
        expect(expectedResult).toEqual(typeAutoriteContractanteCollection);
      });
    });

    describe('compareTypeAutoriteContractante', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTypeAutoriteContractante(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTypeAutoriteContractante(entity1, entity2);
        const compareResult2 = service.compareTypeAutoriteContractante(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTypeAutoriteContractante(entity1, entity2);
        const compareResult2 = service.compareTypeAutoriteContractante(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTypeAutoriteContractante(entity1, entity2);
        const compareResult2 = service.compareTypeAutoriteContractante(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
