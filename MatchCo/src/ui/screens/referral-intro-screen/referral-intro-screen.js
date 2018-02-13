// @flow

import React, { Component } from 'react'
import { Dimensions, View, Text, Image, StyleSheet } from 'react-native'
import { inject, observer } from 'mobx-react'
import type { UserStore } from '../../../stores/user-store'
import type { NavigationStore } from '../../../stores/navigation-store'
import { Button, ButtonPanel, StaticLayout } from '../..'
import { translator } from '../../../services/translate/translator'
import Share from 'react-native-share' // Version 1.0.20
import glam from 'glamorous-native'
import { bind } from 'decko'
import { CurvedText } from '../..'
import { palette } from '../../theme'
const { width, height } = Dimensions.get('window')

const Stretchy = glam.view({ height: 50 })

@translator
@inject('userStore', 'navigationStore')
@observer
export class ReferralIntroScreen extends Component {
  static propTypes: {
    navigation: any
  };

  BottleImage(name, tint) {
    return(
      <View style={styles.wrapper}>
        <View style={[styles.tint, { backgroundColor: tint }]} />
        <Image
          style={{height: height*.3}}
          source={require('../../components/bottle-image/front.png')}
          resizeMode='contain'
        />
        <View style={styles.frontName}>
          <CurvedText text={name} color={palette.deepBlack} fontSize={6} />
        </View>
      </View>
    )
  }

  render () {
    const { userStore, navigationStore, translate } = this.props
    const shareOptions = {
      message: 'I just tried bareMinerals MADE-2-FIT, a beauty app that scans your skin and custom blends a foundation to perfectly match your skintone. The bottle is even printed with your name on it. You\'got to try it out. Use this link for $10 off your first order!',
      url: 'https://mza9.app.link/' + userStore.user.uid
    }

    return (
      <StaticLayout>
        {userStore.isLoggedIn
          ? this.BottleImage(userStore.user.displayName, userStore.user.scanTint)
          : this.BottleImage('Claudia', 'burlywood')
        }
        <Stretchy/>
        <Text style={styles.text}>{translate('referralScreen.description')}</Text>
        {false // TODO: Check to see if their are referrals
          ? <Text style={styles.text}>You have referrals.</Text>
          : <Text style={styles.text}>{translate('referralScreen.noReferrals')}</Text>}
        <Stretchy/>
        <ButtonPanel>
          {userStore.isLoggedIn
            ? <Button text={translate('referralScreen.tellFriend')} onPress={() => Share.open(shareOptions)} />
            : <Button text={translate('referralScreen.signup')} onPress={() => navigationStore.navigateTo('signup')} />}
          {userStore.isLoggedIn
            ? <Button.Clear text={translate('referralScreen.pastReferrals')} onPress={() => navigationStore.navigateTo('pastReferrals')} />
            : <Button.Clear text={translate('referralScreen.login')} onPress={() => navigationStore.navigateTo('login')} />}
        </ButtonPanel>
      </StaticLayout>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: { justifyContent: 'center', alignItems: 'center' },
  frontName: {
    position: 'absolute',
    alignItems: 'center',
    bottom: height*.069
  },
  tint: { position: 'absolute', top: height*.1, width: width*.14, height: height*.2 },
  text: { color: 'black', fontSize: 15 }
})
