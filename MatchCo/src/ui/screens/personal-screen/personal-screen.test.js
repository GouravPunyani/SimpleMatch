import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { PersonalScreen as Screen } from './personal-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
