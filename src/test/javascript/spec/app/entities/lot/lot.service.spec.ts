import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LotService } from 'app/entities/lot/lot.service';
import { ILot, Lot } from 'app/shared/model/lot.model';
import { StareLot } from 'app/shared/model/enumerations/stare-lot.model';

describe('Service Tests', () => {
  describe('Lot Service', () => {
    let injector: TestBed;
    let service: LotService;
    let httpMock: HttpTestingController;
    let elemDefault: ILot;
    let expectedResult: ILot | ILot[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LotService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Lot(0, 0, StareLot.ADJUDECAT, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Lot', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Lot()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Lot', () => {
        const returnedFromService = Object.assign(
          {
            nrFisa: 1,
            stare: 'BBBBBB',
            pretPornire: 1,
            volumBrut: 1,
            volumNet: 1,
            volumCoaja: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Lot', () => {
        const returnedFromService = Object.assign(
          {
            nrFisa: 1,
            stare: 'BBBBBB',
            pretPornire: 1,
            volumBrut: 1,
            volumNet: 1,
            volumCoaja: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Lot', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
