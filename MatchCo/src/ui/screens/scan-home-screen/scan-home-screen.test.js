import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ScanHomeScreen as Screen } from './scan-home-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
