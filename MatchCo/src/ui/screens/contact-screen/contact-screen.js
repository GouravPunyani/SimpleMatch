// @flow

import React, { Component } from 'react'
import { observer, inject } from 'mobx-react'
import { View, StyleSheet, TouchableOpacity, Dimensions } from 'react-native'
import {
  StaticLayout,
  Text,
  ContactSectionHeader,
  ContactMethod,
  ContactSocialButton
} from '../..'
import { spacing, color, fontFamily } from '../../theme'
import { ContactsStore } from '../../../stores/contacts-store'
import { sendEmail, callPhone, visitUrl } from '../../../services/opener'
import { translator } from '../../../services/translate/translator'
const { width, height } = Dimensions.get('window')

export type Props = {
  contactStore: ContactsStore,
  translator: (key: string) => string
};

@translator
@inject('contactsStore')
@observer
export class ContactScreen extends Component {
  render () {
    // grab the state out of the store
    const { translate, contactsStore } = this.props
    const { contacts } = contactsStore

    return (
      <StaticLayout>
        <View style={styles.section}>
          <ContactSectionHeader
            text={translate('contactScreen.customerServiceTitle')}
          />
          <ContactMethod
            text={contacts.customerServiceEmail}
            icon='envelope'
            onPress={() => sendEmail(contacts.customerServiceEmail)}
          />
          <ContactMethod
            text={contacts.customerServicePhone}
            icon='phone'
            onPress={() => callPhone(contacts.customerServicePhone)}
          />
          <Text>
            <Text
              style={styles.returnPolicy}
              text={translate('contactScreen.returnLabel')}
            />
            <Text text={translate('contactScreen.returnPolicy')} />
          </Text>
        </View>

        <View style={styles.section}>
          <ContactSectionHeader
            text={translate('contactScreen.featuresTitle')}
          />
          <ContactMethod
            text={contacts.feedbackEmail}
            icon='envelope'
            onPress={() => sendEmail(contacts.feedbackEmail)}
          />
          <Text text={translate('contactScreen.feedback')} />
        </View>

        <View style={styles.section}>
          <ContactSectionHeader text={translate('contactScreen.mediaTitle')} />
          <ContactMethod
            text={contacts.mediaEmail}
            icon='envelope'
            onPress={() => sendEmail(contacts.mediaEmail)}
          />
        </View>

        <View style={styles.gap} />

        <View style={styles.follow}>
          <Text
            title
            style={styles.followTitle}
            text={translate('contactScreen.followUsTitle')}
          />
          <View style={styles.social}>
            <ContactSocialButton
              network='facebook'
              onPress={() => visitUrl(contacts.facebookUrl)}
            />
            <ContactSocialButton
              network='instagram'
              onPress={() => visitUrl(contacts.instagramUrl)}
            />
            <ContactSocialButton
              network='twitter'
              onPress={() => visitUrl(contacts.twitterUrl)}
            />
          </View>
          <TouchableOpacity
            onPress={() => visitUrl(contacts.companyUrl)}
            style={styles.web}
          >
            <Text style={styles.url} text={translate('contactScreen.www')} />
          </TouchableOpacity>
        </View>
      </StaticLayout>
    )
  }
}

const styles = StyleSheet.create({
  section: { paddingBottom: spacing.large },
  follow: { alignItems: 'center' },
  followTitle: {
    color: color.brand,
    fontFamily: fontFamily.sangBlueLightItalic,
    fontSize: 18
  },
  returnPolicy: { fontFamily: fontFamily.sangBlueLightItalic },
  social: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    width: width*.5,
    paddingTop: spacing.medium
  },
  gap: { flex: 1 },
  web: { minHeight: 44, justifyContent: 'center' },
  url: { color: color.brand }
})
