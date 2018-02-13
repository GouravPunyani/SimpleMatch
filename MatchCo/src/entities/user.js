import { observable } from 'mobx'

/**
 * The known list of reasons for being unable to login.
 */
export type LoginErrorCode = 'wrong' | 'unknown';

export class User {
  @observable uid: ?string
  @observable email: ?string
  @observable displayName: ?string = 'Default Username' // TODO(steve): escalate
  @observable photoURL: ?string
  @observable fetching: boolean = false
  @observable errorCode: LoginErrorCode
  @observable scanTint: string = 'hotpink'
  @observable scanDate: string = 'May 32, 2017'
}
