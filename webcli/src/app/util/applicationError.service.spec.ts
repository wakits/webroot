import { TestBed, inject } from '@angular/core/testing';

import { ApplicationerrorService } from './applicationerror.service';

describe('ApplicationerrorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApplicationerrorService]
    });
  });

  it('should be created', inject([ApplicationerrorService], (service: ApplicationerrorService) => {
    expect(service).toBeTruthy();
  }));
});
