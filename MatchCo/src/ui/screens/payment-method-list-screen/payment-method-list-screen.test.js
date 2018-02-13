import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { PaymentMethodListScreen as Screen } from './payment-method-list-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
