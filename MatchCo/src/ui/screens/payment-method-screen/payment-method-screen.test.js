import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { PaymentMethodScreen as Screen } from './payment-method-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
