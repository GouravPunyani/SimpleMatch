// @flow
import type { FirebaseService } from '../services/firebase'

import { ContactsStore } from './contacts-store'
import { UserStore } from './user-store'
import { NavigationStore } from './navigation-store'
import { ProductsStore } from './products-store'
import { ScanStore } from './scan-store'

export type Services = {
  firebase: FirebaseService
}

export type SerializedState = {
  contacts: {},
  user: {},
  navigation: {},
  products: {},
  scan: {}
}

/**
 * Holds the domain-specific stores.
 */
export class Store {
  /** Manages contact settings like calling customer support, etc. */
  contacts: ContactsStore

  /** Manages the user's profile, logging, and logging out. */
  user: UserStore

  /** Handles the screen navigation. */
  navigation: NavigationStore

  /** Manages the display of products. */
  products: ProductsStore

  /** Manages the scan storage */
  scan: ScanStore

  constructor (state?: SerializedState) {
    this.contacts = new ContactsStore(state && state.contacts)
    this.user = new UserStore(state && state.user)
    this.navigation = new NavigationStore(state && state.navigation)
    this.products = new ProductsStore(state && state.products)
    this.scan = new ScanStore(state && state.scan)
  }

  /**
   * Connnects the services to the stores.
   */
  setServices (services: Services) {
    this.user.firebase = services.firebase
  }

  /**
   * Saves the state to a JSON-able object.
   */
  serialize (): SerializedState {
    return {
      contacts: this.contacts.serialize(),
      user: this.user.serialize(),
      navigation: this.navigation.serialize(),
      products: this.products.serialize(),
      scan: this.scan.serialize()
    }
  }
}

/**
 * The global store.
 */
let store: Store

/**
 * Sets the store from the outside.
 */
export function setStore (s: Store) {
  store = s
}

/**
 * Gets the store.
 */
export function getStore () {
  return store
}
