import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZegoChatComponent } from './zego-chat.component';

describe('ZegoChatComponent', () => {
  let component: ZegoChatComponent;
  let fixture: ComponentFixture<ZegoChatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ZegoChatComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ZegoChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
