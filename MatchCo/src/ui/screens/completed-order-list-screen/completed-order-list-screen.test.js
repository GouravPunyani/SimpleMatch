import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { CompletedOrderListScreen as Screen } from './completed-order-list-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
