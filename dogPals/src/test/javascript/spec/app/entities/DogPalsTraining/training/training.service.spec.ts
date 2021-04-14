import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TrainingService } from 'app/entities/DogPalsTraining/training/training.service';
import { ITraining, Training } from 'app/shared/model/DogPalsTraining/training.model';

describe('Service Tests', () => {
  describe('Training Service', () => {
    let injector: TestBed;
    let service: TrainingService;
    let httpMock: HttpTestingController;
    let elemDefault: ITraining;
    let expectedResult: ITraining | ITraining[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TrainingService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Training(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_TIME_FORMAT),
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Training', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_TIME_FORMAT),
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            startTime: currentDate,
            endTime: currentDate,
          },
          returnedFromService
        );

        service.create(new Training()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Training', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            date: currentDate.format(DATE_TIME_FORMAT),
            details: 'BBBBBB',
            location: 'BBBBBB',
            price: 1,
            agency: 'BBBBBB',
            bookingStatus: 'BBBBBB',
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
            slot: 1,
            popularity: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            startTime: currentDate,
            endTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Training', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            date: currentDate.format(DATE_TIME_FORMAT),
            details: 'BBBBBB',
            location: 'BBBBBB',
            price: 1,
            agency: 'BBBBBB',
            bookingStatus: 'BBBBBB',
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
            slot: 1,
            popularity: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            startTime: currentDate,
            endTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Training', () => {
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
