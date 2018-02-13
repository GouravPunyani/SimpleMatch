// @flow

import { observable, toJS } from 'mobx'
import { Contacts, createDefault } from '../entities/contacts'

export class ContactsStore {
  @observable contacts: Contacts = createDefault()

  constructor (state?: {}) {
    if (state) {
      this.contacts = new Contacts(state)
    }
  }

  serialize (): Object {
    return toJS(this.contacts)
  }
}
