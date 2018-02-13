import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { LoginScreen as Screen } from './login-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
