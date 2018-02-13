// @flow

import React, { Component } from 'react'
import { View, Text, StyleSheet } from 'react-native'
import { ButtonPanel, ScrollingLayout } from '../..'
import { translator } from '../../../services/translate/translator'

var refNum = 0 // TODO: Find number of referrals done

@translator
export class PastReferralsScreen extends Component {
  static propTypes: {
  };

  render () {
    const { translate } = this.props
    return (
      <ScrollingLayout>
        <View style={styles.wrapper}>
          <Text style={styles.text}>{translate('pastReferralsScreen.credit') + refNum}</Text>
        </View><View style={{flex: 1, height: 1, backgroundColor: '#dcb8bd'}}/>
        <View style={styles.wrapper}>
          {false // TODO: Check to see if their are referrals
            ? <Text style={styles.text}>You have referrals.</Text> // TODO: Show past referrals
            : <Text style={styles.text}>{translate('pastReferralsScreen.noReferrals')}</Text>}
        </View>
      </ScrollingLayout>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    backgroundColor: 'white',
    alignItems: 'center',
    paddingVertical: 10
  },
  text: { color: 'black', fontSize: 15 }
})
