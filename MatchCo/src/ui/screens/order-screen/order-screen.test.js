import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { OrderScreen as Screen } from './order-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
