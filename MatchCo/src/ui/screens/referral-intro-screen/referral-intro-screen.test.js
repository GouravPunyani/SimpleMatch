import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ReferralIntroScreen as Screen } from './referral-intro-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
