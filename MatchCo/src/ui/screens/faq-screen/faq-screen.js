// @flow

import React, { Component } from 'react'
import { View, Text, StyleSheet } from 'react-native'
import { ButtonPanel, ScrollingLayout } from '../..'
import Accordion from 'react-native-collapsible/Accordion' // Used to create the collapsible text
import { translator } from '../../../services/translate/translator'

@translator
export class FaqScreen extends Component {
  static propTypes: {
  };

  THEPRODUCT() {
    const {translate} = this.props
    return (
      [{title: translate('faqsScreen.whatIsItQuestion'), content: translate('faqsScreen.whatIsItAnswer')},
      {title: translate('faqsScreen.ingredientsQuestion'), content: translate('faqsScreen.ingredientsAnswer')},
      {title: translate('faqsScreen.durationQuestion'), content: translate('faqsScreen.durationAnswer')},
      {title: translate('faqsScreen.howToApplyQuestion'), content: translate('faqsScreen.howToApplyAnswer')}]
    )
  }

  THEAPP() {
    const {translate} = this.props
    return (
      [{title: translate('faqsScreen.howDoIKnowQuestion'), content: translate('faqsScreen.howDoIKnowAnswer')},
      {title: translate('faqsScreen.howDoesItWorkQuestion'), content: translate('faqsScreen.howDoesItWorkAnswer')},
      {title: translate('faqsScreen.caseRemovalQuestion'), content: translate('faqsScreen.caseRemovalAnswer')},
      {title: translate('faqsScreen.whatDevicesWorkQuestion'), content: translate('faqsScreen.whatDevicesWorkAnswer')},
      {title: translate('faqsScreen.whatOSQuestion'), content: translate('faqsScreen.whatOSAnswer')},
      {title: translate('faqsScreen.areScansSavedQuestion'), content: translate('faqsScreen.areScansSavedAnswer')}]
    )
  }


  THEPROCESS() {
    const {translate} = this.props
    return (
      [{title: translate('faqsScreen.willMakeUpAffectQuestion'), content: translate('faqsScreen.willMakeUpAffectAnswer')},
      {title: translate('faqsScreen.scanOrderHourQuestion'), content: translate('faqsScreen.scanOrderHourAnswer')},
      {title: translate('faqsScreen.whySheetsQuestion'), content: translate('faqsScreen.whySheetsAnswer')},
      {title: translate('faqsScreen.tipsToNailQuestion'), content: translate('faqsScreen.tipsToNailAnswer')},
      {title: translate('faqsScreen.howOftenScanQuestion'), content: translate('faqsScreen.howOftenScanAnswer')}]
    )
  }

  YOURORDER() {
    const {translate} = this.props
    return (
      [{title: translate('faqsScreen.canIreOrderQuestion'), content: translate('faqsScreen.canIreOrderAnswer')},
      {title: translate('faqsScreen.whatIfDamageQuestion'), content: translate('faqsScreen.whatIfDamageAnswer')},
      {title: translate('faqsScreen.howDoIReturnQuestion'), content: translate('faqsScreen.howDoIReturnAnswer')},
      {title: translate('faqsScreen.cancellationsQuestion'), content: translate('faqsScreen.cancellationsAnswer')},
      {title: translate('faqsScreen.howChooseNameQuestion'), content: translate('faqsScreen.howChooseNameAnswer')},
      {title: translate('faqsScreen.friendsAndBenefitsQuestion'), content: translate('faqsScreen.friendsAndBenefitsAnswer')}]
    )
  }

  SHIPPINGANDPAYMENT() {
    const {translate} = this.props
    return (
      [{title: translate('faqsScreen.whatShippingQuestion'), content: translate('faqsScreen.whatShippingAnswer')},
      {title: translate('faqsScreen.canPOBoxesQuestion'), content: translate('faqsScreen.canPOBoxesAnswer')},
      {title: translate('faqsScreen.canInternationalQuestion'), content: translate('faqsScreen.canInternationalAnswer')},
      {title: translate('faqsScreen.canIExpediteQuestion'), content: translate('faqsScreen.canIExpediteAnswer')},
      {title: translate('faqsScreen.canITrackQuestion'), content: translate('faqsScreen.canITrackAnswer')},
      {title: translate('faqsScreen.whoToContactQuestion'), content: translate('faqsScreen.howToContactAnswer')},
      {title: translate('faqsScreen.whatPaymentQuestion'), content: translate('faqsScreen.whatPaymentAnswer')},
      {title: translate('faqsScreen.whenChargedQuestion'), content: translate('faqsScreen.whenChargedAnswer')},
      {title: translate('faqsScreen.howLongQuestion'), content: translate('faqsScreen.howLongAnswer')}]
    )
  }

  GENERAL(){
    const {translate} = this.props
    return (
      [{title: translate('faqsScreen.doYouPromotionsQuestion'), content: translate('faqsScreen.doYouPromotionsAnswer')}]
    )
  }

  _renderHeader(section) {
    return (
      <View>
        <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
        <Text style={styles.subtitle}>{section.title}</Text>
      </View>
    )
  }

  _renderContent(section) {
    return (
      <View>
        <View style={{flex: 1, height: 0.5, backgroundColor: 'gainsboro'}}/>
        <Text style={styles.text}>{section.content}</Text>
      </View>
    )
  }

  render () {
    const {translate} = this.props
    return (
      <ScrollingLayout>
        <ButtonPanel>
          <Text style={styles.title}>{translate('faqsScreen.theProduct')}</Text>
          <View style={{flex: 1, height: 1, backgroundColor: '#dcb8bd'}}/>
          <Accordion
            sections={this.THEPRODUCT()}
            renderHeader={this._renderHeader}
            renderContent={this._renderContent}
            underlayColor={'lightgray'}
          />
          <Text style={styles.title}>{translate('faqsScreen.theApp')}</Text>
          <View style={{flex: 1, height: 1, backgroundColor: '#dcb8bd'}}/>
          <Accordion
            sections={this.THEAPP()}
            renderHeader={this._renderHeader}
            renderContent={this._renderContent}
            underlayColor={'lightgray'}
          />
          <Text style={styles.title}>{translate('faqsScreen.theScanningProcess')}</Text>
          <View style={{flex: 1, height: 1, backgroundColor: '#dcb8bd'}}/>
          <Accordion
            sections={this.THEPROCESS()}
            renderHeader={this._renderHeader}
            renderContent={this._renderContent}
            underlayColor={'lightgray'}
          />
          <Text style={styles.title}>{translate('faqsScreen.yourOrder')}</Text>
          <View style={{flex: 1, height: 1, backgroundColor: '#dcb8bd'}}/>
          <Accordion
            sections={this.YOURORDER()}
            renderHeader={this._renderHeader}
            renderContent={this._renderContent}
            underlayColor={'lightgray'}
          />
          <Text style={styles.title}>{translate('faqsScreen.shippingPaymentOptions')}</Text>
          <View style={{flex: 1, height: 1, backgroundColor: '#dcb8bd'}}/>
          <Accordion
            sections={this.SHIPPINGANDPAYMENT()}
            renderHeader={this._renderHeader}
            renderContent={this._renderContent}
            underlayColor={'lightgray'}
          />
          <Text style={styles.title}>{translate('faqsScreen.general')}</Text>
          <View style={{flex: 1, height: 1, backgroundColor: '#dcb8bd'}}/>
          <Accordion
            sections={this.GENERAL()}
            renderHeader={this._renderHeader}
            renderContent={this._renderContent}
            underlayColor={'lightgray'}
          />
        </ButtonPanel>
      </ScrollingLayout>
    )
  }
}

const styles = StyleSheet.create({
  title: { color: 'black', fontSize: 15, fontWeight: 'bold', padding: 7 },
  subtitle: { color: 'dimgray', fontSize: 12, fontFamily: 'NexaBold', padding: 10 },
  text: { color: 'dimgray', fontSize: 12, padding: 10 }
})
