// @flow
import '../services/reactotron'
import React, { Component } from 'react'
import { DrawerNavigatorContainer } from '../ui/navigation/drawer-navigator/drawer-container'
// import { RootContainer } from '../ui/navigation/root-navigator/root-container'
import createFirebase from '../services/firebase'
import { setStore, Store } from '../stores'
import { Provider as StoreProvider } from 'mobx-react'
import { TranslateProvider } from '../services/translate/translate-provider'

let store
export class App extends Component {
  componentWillMount () {
    const firebase = createFirebase()
    store = new Store()
    store.setServices({ firebase })
    store.scan.getLatestScan()
    setStore(store)
  }

  render () {
    return (
      <TranslateProvider>
        <StoreProvider
          contactsStore={store.contacts}
          userStore={store.user}
          navigationStore={store.navigation}
          productsStore={store.products}
          scanStore={store.scan}
        >
          <DrawerNavigatorContainer />
        </StoreProvider>
      </TranslateProvider>
    )
  }
}
