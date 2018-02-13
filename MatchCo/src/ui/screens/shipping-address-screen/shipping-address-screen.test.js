import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ShippingAddressScreen as Screen } from './shipping-address-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
