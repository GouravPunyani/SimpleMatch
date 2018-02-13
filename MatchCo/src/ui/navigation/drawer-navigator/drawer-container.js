// @flow

import type { NavigationStore } from '../../../stores/navigation-store'
import * as ReactNavigation from 'react-navigation'
import { DrawerNavigator } from './drawer-navigator'
import { inject, observer } from 'mobx-react'
import React, { Component } from 'react'

export type Props = { navigationStore: NavigationStore };

@inject('navigationStore')
@observer
export class DrawerNavigatorContainer extends Component<*, Props, *> {
  render () {
    const navigation = ReactNavigation.addNavigationHelpers({
      dispatch: this.props.navigationStore.navigate,
      state: this.props.navigationStore.navigationState
    })

    return <DrawerNavigator navigation={navigation} />
  }
}
