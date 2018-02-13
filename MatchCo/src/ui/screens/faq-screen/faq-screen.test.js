import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { FaqScreen as Screen } from './faq-screen'

test('renders correctly', () => {
  const tree = renderer.create(<Screen />)
  expect(tree).toMatchSnapshot()
})
