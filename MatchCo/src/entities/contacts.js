// @flow
import { observable } from 'mobx'

export class Contacts {
  @observable customerServiceEmail: ?string
  @observable feedbackEmail: ?string
  @observable mediaEmail: ?string
  @observable customerServicePhone: ?string
  @observable facebookUrl: ?string
  @observable instagramUrl: ?string
  @observable twitterUrl: ?string
  @observable companyUrl: ?string
}

export const createDefault = () => {
  const contacts = new Contacts()
  contacts.customerServiceEmail = 'made2fit@bareminerals.com'
  contacts.feedbackEmail = 'made2fit@bareminerals.com'
  contacts.mediaEmail = 'made2fit@bareminerals.com'
  contacts.customerServicePhone = '800.605.8816'
  contacts.facebookUrl = 'https://www.facebook.com/bareminerals?fref=ts'
  contacts.instagramUrl = 'https://instagram.com/bareminerals'
  contacts.twitterUrl = 'https://twitter.com/bareminerals'
  contacts.companyUrl = 'http://www.bareminerals.com'
  return contacts
}
