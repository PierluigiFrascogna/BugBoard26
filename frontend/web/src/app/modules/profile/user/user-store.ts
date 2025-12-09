import { computed, inject, Injectable, Signal } from '@angular/core';
import { UserApi } from './user-api';
import { AuthStore } from '../../../core/auth/auth-store';
import { User } from './user';

@Injectable({
  providedIn: 'root',
})
export class UserStore {
  private readonly api = inject(UserApi);
  private auth = inject(AuthStore);
  
  readonly uuid: Signal<string | null> = computed(() => this.auth.uuid());
  readonly name: Signal<string | null>  = computed(() => this.auth.name());
  readonly surname: Signal<string | null>  = computed(() => this.auth.surname());
  readonly role: Signal<string | null> = computed(() => this.auth.role());

  private readonly isValid: Signal<boolean> = computed(() => {
    return [
      this.uuid(),
      this.name(),
      this.surname(),
      this.role()
    ].every((e) => e !== null);
  });

  readonly object: Signal<User | null> = computed(() => {
    if(!this.isValid()) {
      return null;
    }

    return {
      uuid: this.uuid()!,
      name: this.name()!,
      surname: this.surname()!,
      role: this.role()!
    }
  });
  
}
