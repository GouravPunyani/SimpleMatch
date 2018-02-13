// @flow
import { StackNavigator } from 'react-navigation'
import { DrawerNavigator } from '../drawer-navigator/drawer-navigator'
import screenMap from '../screen-map'

export const RootNavigator = StackNavigator({
  login: { screen: screenMap.login },
  dashboardStack: { screen: DrawerNavigator }
})
