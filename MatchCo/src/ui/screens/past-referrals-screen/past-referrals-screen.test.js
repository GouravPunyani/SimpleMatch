import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { PastReferralsScreen as Screen } from './past-referrals-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
