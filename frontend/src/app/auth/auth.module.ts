import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ActivateComponent } from './pages/activate/activate.component';
import { AuthRoutingModule } from './auth-routing.module';
import { FormsModule } from '@angular/forms';
import { InputComponent } from '../shared/components/input/input.component';

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ActivateComponent,
    InputComponent,
  ],
  imports: [CommonModule, FormsModule, AuthRoutingModule],
})
export class AuthModule {}
