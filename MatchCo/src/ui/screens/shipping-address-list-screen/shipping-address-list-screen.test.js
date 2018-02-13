import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ShippingAddressListScreen as Screen } from './shipping-address-list-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
