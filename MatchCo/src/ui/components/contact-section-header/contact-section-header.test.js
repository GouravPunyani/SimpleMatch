// @flow
import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { ContactSectionHeader as Comp } from './contact-section-header'

test('renders correctly', () => {
  const tree = renderer.create(<Comp text='foo' />)
  expect(tree).toMatchSnapshot()
})
