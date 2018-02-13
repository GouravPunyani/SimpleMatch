// @flow

import React, { Component } from 'react'
import { ScrollView, View, Text, StyleSheet, Image, Dimensions } from 'react-native'
import { Button, ButtonPanel, ScrollingLayout } from '../..'
import { translator } from '../../../services/translate/translator'
const { width, height } = Dimensions.get('window')

@translator
export default class AccountScreen extends Component {
  static propTypes: {
    navigation: any
  }

  render () {
    const { navigation, translate } = this.props
    const navigate = (path, options = {}) => () => navigation.navigate(path, options)

    return (
      <ScrollingLayout>
        <ButtonPanel>
          <Text style={styles.title}>{translate('accountScreen.orders')}</Text>
          <View style={{flex: 1, height: 1, backgroundColor: '#dcb8bd'}}/>
          <View><Image style={styles.arrow} source={require('../../images/Arrow right.png')} />
          <Button text={translate('accountScreen.currentOrders')} onPress={navigate('currentOrderList')} textStyle={{color: 'dimgray', fontSize: 12}}
          style={{alignItems: 'flex-start', minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}}/></View>
          <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
          <View><Image style={styles.arrow} source={require('../../images/Arrow right.png')} />
          <Button text={translate('accountScreen.completedOrders')} onPress={navigate('completedOrderList')} textStyle={{color: 'dimgray', fontSize: 12}}
          style={{alignItems: 'flex-start', minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} /></View>
          <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
          <Text style={styles.title}>{translate('accountScreen.settings')}</Text>
          <View style={{flex: 1, height: 1, backgroundColor: '#dcb8bd'}}/>
          <View><Image style={styles.arrow} source={require('../../images/Arrow right.png')} />
          <Button text={translate('accountScreen.personalInformation')} onPress={navigate('personal')} textStyle={{color: 'dimgray', fontSize: 12}}
          style={{alignItems: 'flex-start', minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} /></View>
          <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
          <View><Image style={styles.arrow} source={require('../../images/Arrow right.png')} />
          <Button text={translate('accountScreen.paymentMethod')} onPress={navigate('paymentMethodList')} textStyle={{color: 'dimgray', fontSize: 12}}
          style={{alignItems: 'flex-start', minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} /></View>
          <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
          <View><Image style={styles.arrow} source={require('../../images/Arrow right.png')} />
          <Button text={translate('accountScreen.shippingAddress')} onPress={navigate('shippingAddressList')} textStyle={{color: 'dimgray', fontSize: 12}}
          style={{alignItems: 'flex-start', minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} /></View>
          <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
          <Text style={styles.title}>{translate('accountScreen.information')}</Text>
          <View style={{flex: 1, height: 1, backgroundColor: '#dcb8bd'}}/>
          <View><Image style={styles.arrow} source={require('../../images/Arrow right.png')} />
          <Button text={translate('accountScreen.contactUs')} onPress={navigate('contact')} textStyle={{color: 'dimgray', fontSize: 12}}
          style={{alignItems: 'flex-start', minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} /></View>
          <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
          <View><Image style={styles.arrow} source={require('../../images/Arrow right.png')} />
          <Button text={translate('accountScreen.FAQs')} onPress={navigate('faq')} textStyle={{color: 'dimgray', fontSize: 12}}
          style={{alignItems: 'flex-start', minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} /></View>
          <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
          <View><Image style={styles.arrow} source={require('../../images/Arrow right.png')} />
          <Button text={translate('accountScreen.privacyPolicy')} onPress={navigate('privacyPolicy')} textStyle={{color: 'dimgray', fontSize: 12}}
          style={{alignItems: 'flex-start', minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} /></View>
          <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
          <View><Image style={styles.arrow} source={require('../../images/Arrow right.png')} />
          <Button text={translate('accountScreen.termsOfUse')} onPress={navigate('termsOfUse')} textStyle={{color: 'dimgray', fontSize: 12}}
          style={{alignItems: 'flex-start', minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} /></View>
          <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
        </ButtonPanel>
      </ScrollingLayout>
    )
  }
}

const styles = StyleSheet.create({
  arrow: {position: 'absolute', width: 10, height: 10, left: width*.9, bottom: 12.5},
  button: {alignItems: 'flex-start', minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0},
  buttonText: {color: 'dimgray', fontSize: 12},
  title: { color: 'black', lineHeight: 30, fontSize: 15, fontWeight: 'bold', padding: 7 }
})
