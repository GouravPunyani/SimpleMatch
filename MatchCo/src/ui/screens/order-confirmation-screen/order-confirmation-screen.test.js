import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { OrderConfirmationScreen as Screen } from './order-confirmation-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
