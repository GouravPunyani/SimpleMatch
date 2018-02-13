// @flow

import React, { Component } from 'react'
import { View, Text, StyleSheet } from 'react-native'
import { ScrollingLayout } from '../..'
import { translator } from '../../../services/translate/translator'

@translator
export class PrivacyPolicyScreen extends Component {
  static propTypes: {
  };

  render () {
    const { translate } = this.props
    return (
      <ScrollingLayout>
        <View style={styles.wrapper}>
          <Text style={styles.text}>{translate('privacyPolicy')}</Text>
        </View>
      </ScrollingLayout>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
    backgroundColor: 'white',
    justifyContent: 'center',
    alignItems: 'center'
  },
  text: { color: 'black', fontSize: 9, padding: 10 }
})
