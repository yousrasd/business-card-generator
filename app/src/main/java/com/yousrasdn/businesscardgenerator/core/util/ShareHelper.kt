package com.yousrasdn.businesscardgenerator.core.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.yousrasdn.businesscardgenerator.R
import com.yousrasdn.businesscardgenerator.domain.model.BusinessCard

object ShareHelper {
    
    fun shareViaApps(context: Context, card: BusinessCard) {
        val shareText = buildShareText(card)
        
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "${card.firstName} ${card.lastName} - Contact Info")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        
        val chooser = Intent.createChooser(intent, context.getString(R.string.share_via_apps))
        context.startActivity(chooser)
    }
    
    fun shareViaEmail(context: Context, card: BusinessCard) {
        val shareText = buildShareText(card)
        
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_SUBJECT, "${card.firstName} ${card.lastName} - Contact Information")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        
        try {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_via_email)))
        } catch (e: Exception) {
            Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }
    
    fun shareViaSMS(context: Context, card: BusinessCard) {
        val shareText = buildShareText(card)
        
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra("sms_body", shareText)
        }
        
        try {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_via_sms)))
        } catch (e: Exception) {
            Toast.makeText(context, "No SMS app found", Toast.LENGTH_SHORT).show()
        }
    }
    
    fun copyToClipboard(context: Context, card: BusinessCard) {
        val shareText = buildShareText(card)
        
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Contact Info", shareText)
        clipboard.setPrimaryClip(clip)
        
        Toast.makeText(context, context.getString(R.string.share_copied), Toast.LENGTH_SHORT).show()
    }
    
    private fun buildShareText(card: BusinessCard): String {
        return buildString {
            appendLine("📇 ${card.firstName} ${card.lastName}")
            appendLine()
            appendLine("💼 ${card.jobTitle}")
            appendLine("🏢 ${card.company}")
            appendLine()
            appendLine("📧 ${card.email}")
            if (card.phone.isNotBlank()) {
                appendLine("📱 ${card.phone}")
            }
            if (card.website.isNotBlank()) {
                appendLine("🌐 ${card.website}")
            }
        }
    }
}
