import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { OrderReviewScreen as Screen } from './order-review-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
