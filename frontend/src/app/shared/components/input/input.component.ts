import { Component, Input, forwardRef } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { NgModelBase } from '../NgModelBase';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputComponent),
      multi: true,
    },
  ],
})
export class InputComponent extends NgModelBase {
  @Input('type') typeProps: 'email' | 'password' | 'text' | undefined;
  @Input('label') labelProps: string | undefined;
  @Input('icon') iconProps: string | undefined;
}
