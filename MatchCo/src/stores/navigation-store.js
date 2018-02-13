// @flow

import { observable, toJS, action } from 'mobx'
import { DrawerNavigator } from '../ui/navigation/drawer-navigator/drawer-navigator'
import { NavigationActions } from 'react-navigation'
import { bind } from 'decko'

const { getStateForAction } = DrawerNavigator.router

const defaultState = getStateForAction(
  NavigationActions.navigate({ routeName: 'dashboardStack' })
)

export class NavigationStore {
  @observable.ref navigationState = defaultState

  constructor (state?: {}) {
    if (state) {
      this.navigationState = state
    }
  }

  /**
   * Serialize this state.
   */
  serialize (): Object {
    return toJS(this.navigationState)
  }

  /**
   * Navigates to a new screen via the `react-nativation` API.
   */
  @bind
  @action
  navigate (action: {}, shouldPush: boolean = true) {
    const previousNavState = shouldPush ? this.navigationState : null
    this.navigationState = getStateForAction(action, previousNavState) || this.navigationState
  }

  /**
   * Go to a new screen.
   */
  @bind
  @action
  navigateTo (routeName: string) {
    this.navigate(NavigationActions.navigate({ routeName }))
  }
}
