// @flow

import React from 'react'
import * as ReactNavigation from 'react-navigation'
import { DrawerContent } from './drawer-content'
import { pipe, reduce, assoc, keys } from 'ramda'
import screenMap from '../screen-map'

const allScreens = keys(screenMap)

const dashboardScreens = ['dashboard', ...allScreens]
const scanScreens = ['scanHome', ...allScreens]
const productScreens = ['product', ...allScreens]
const accountScreens = ['account', ...allScreens]
const referralScreens = ['referralIntro', ...allScreens]
const howToScanScreens = ['scanHome', ...allScreens]
const faqScreens = ['faq', ...allScreens]
const contactScreens = ['contact', ...allScreens]
const loginScreens = ['login', ...allScreens]

// Builds a StackNavigator out of a list of screen keys
const stackToNavigator = pipe(
  reduce((acc, key) => assoc(key, { screen: screenMap[key] }, acc), {}),
  config => ReactNavigation.StackNavigator(config)
)

export const DrawerNavigator = ReactNavigation.DrawerNavigator(
  {
    dashboardStack: { screen: stackToNavigator(dashboardScreens) },
    scanStack: { screen: stackToNavigator(scanScreens) },
    productStack: { screen: stackToNavigator(productScreens) },
    accountStack: { screen: stackToNavigator(accountScreens) },
    referralStack: { screen: stackToNavigator(referralScreens) },
    howToScanStack: { screen: stackToNavigator(howToScanScreens) },
    faqStack: { screen: stackToNavigator(faqScreens) },
    contactStack: { screen: stackToNavigator(contactScreens) },
    loginStack: { screen: stackToNavigator(loginScreens) }
  },
  {
    backBehavior: 'none',
    contentComponent: props => <DrawerContent {...props} />
  }
)
