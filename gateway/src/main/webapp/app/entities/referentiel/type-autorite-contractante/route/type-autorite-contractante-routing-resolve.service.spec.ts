import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITypeAutoriteContractante } from '../type-autorite-contractante.model';
import { TypeAutoriteContractanteService } from '../service/type-autorite-contractante.service';

import { TypeAutoriteContractanteRoutingResolveService } from './type-autorite-contractante-routing-resolve.service';

describe('TypeAutoriteContractante routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TypeAutoriteContractanteRoutingResolveService;
  let service: TypeAutoriteContractanteService;
  let resultTypeAutoriteContractante: ITypeAutoriteContractante | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(TypeAutoriteContractanteRoutingResolveService);
    service = TestBed.inject(TypeAutoriteContractanteService);
    resultTypeAutoriteContractante = undefined;
  });

  describe('resolve', () => {
    it('should return ITypeAutoriteContractante returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeAutoriteContractante = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTypeAutoriteContractante).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeAutoriteContractante = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTypeAutoriteContractante).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITypeAutoriteContractante>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTypeAutoriteContractante = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTypeAutoriteContractante).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
