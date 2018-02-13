import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ScanScreen as Screen } from './scan-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
