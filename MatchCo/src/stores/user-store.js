// @flow
import type { FirebaseService } from '../services/firebase'
import { User } from '../entities/user'
import { observable, toJS, action, computed } from 'mobx'
import { bind } from 'decko'

export class UserStore {
  @observable user: User = new User()
  firebase: FirebaseService

  constructor (state?: {}) {
    if (state) {
      this.user = new User(state)
    }
  }

  serialize (): Object {
    return toJS(this.user)
  }

  @computed get isLoggedIn (): boolean {
    return Boolean(this.user.email)
  }

  @bind
  @action
  async login (email: string, password: string): Promise<boolean> {
    try {
      this.user.errorCode = null
      this.user.fetching = true
      const response = await this.firebase.signIn(email, password)
      this.user.uid = response.uid
      this.user.displayName = response.displayName || 'No Firebase Name' // TODO(steve): escalate
      this.user.photoURL = response.photoURL
      this.user.email = response.email
      this.user.errorCode = null
      this.user.fetching = false
    } catch (err) {
      this.user.fetching = false
      // TODO(steve): talk to Nick about the conditions they want to show the user
      if (err.code === 'auth/wrong-password') {
        this.user.errorCode = 'wrong'
      } else {
        this.user.errorCode = 'unknown'
      }
    }
    return this.user
  }

  @action async logout (): Promise<void> {
    try {
      await this.firebase.signOut()
    } catch (err) {
    }
    this.user = new User()
  }
}
