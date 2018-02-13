import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import Screen from './account-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
