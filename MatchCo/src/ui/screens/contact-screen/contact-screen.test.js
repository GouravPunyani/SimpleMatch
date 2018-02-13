// @flow
import 'react-native'
import React from 'react'
import renderer from 'react-test-renderer'
import { createDefault } from '../../../entities/contacts'
import { ContactScreen as Screen } from './contact-screen'

test('renders correctly', () => {
  const contacts = createDefault()
  const contactsStore = { contacts }
  const tree = renderer.create(<Screen contactsStore={contactsStore} />)
  expect(tree).toMatchSnapshot()
})
