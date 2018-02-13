import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { TermsOfUseScreen as Screen } from './terms-of-use-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
