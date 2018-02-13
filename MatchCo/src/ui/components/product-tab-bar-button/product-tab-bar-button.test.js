// @flow

import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ProductTabBarButton as Comp } from './product-tab-bar-button'

test('renders correctly', () => {
  const onPress = () => {}
  const tree = renderer.create(<Comp text='foo' onPress={onPress} selected />)
  expect(tree).toMatchSnapshot()
})
