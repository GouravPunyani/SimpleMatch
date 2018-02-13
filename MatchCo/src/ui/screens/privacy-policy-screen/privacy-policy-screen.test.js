import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { PrivacyPolicyScreen as Screen } from './privacy-policy-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
