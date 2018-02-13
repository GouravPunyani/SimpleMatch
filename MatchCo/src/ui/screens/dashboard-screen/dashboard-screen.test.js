// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { User } from '../../../entities/user'
import { DashboardScreen as Screen } from './dashboard-screen'

test('renders correctly', () => {
  const user = new User()
  user.email = 'steve@infinite.red'
  user.fetching = false
  user.uid = '123'
  const userStore = { user }

  const navigationState = {}
  const navigationStore = { navigationState }

  const tree = renderer.create(
    <Screen userStore={userStore} navigationStore={navigationStore} />
  )
  expect(tree).toMatchSnapshot()
})
