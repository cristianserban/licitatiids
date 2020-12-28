import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LicitatieService } from 'app/entities/licitatie/licitatie.service';
import { ILicitatie, Licitatie } from 'app/shared/model/licitatie.model';
import { TipLicitatie } from 'app/shared/model/enumerations/tip-licitatie.model';

describe('Service Tests', () => {
  describe('Licitatie Service', () => {
    let injector: TestBed;
    let service: LicitatieService;
    let httpMock: HttpTestingController;
    let elemDefault: ILicitatie;
    let expectedResult: ILicitatie | ILicitatie[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LicitatieService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Licitatie(0, currentDate, false, TipLicitatie.INCHISA);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataLicitatie: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Licitatie', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataLicitatie: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataLicitatie: currentDate,
          },
          returnedFromService
        );

        service.create(new Licitatie()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Licitatie', () => {
        const returnedFromService = Object.assign(
          {
            dataLicitatie: currentDate.format(DATE_FORMAT),
            activa: true,
            tipLicitatie: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataLicitatie: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Licitatie', () => {
        const returnedFromService = Object.assign(
          {
            dataLicitatie: currentDate.format(DATE_FORMAT),
            activa: true,
            tipLicitatie: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataLicitatie: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Licitatie', () => {
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
