package com.farzan.chat.pageobjects;


import com.farzan.chat.model.ChatMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ChatPage {

    @FindBy(id = "messageText")
    private WebElement messageField;

    @FindBy(id = "messageType")
    private WebElement messageType;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(className="chatMessageUsername")
    private WebElement messageUsername;

    @FindBy(className="chatMessageText")
    private WebElement messageText;

    public ChatPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public ChatMessage getFirstChatMessage(){
        ChatMessage firstChatMessage = new ChatMessage();
        firstChatMessage.setMessageText(this.messageText.getText());
        firstChatMessage.setUsername(this.messageUsername.getText());
        return firstChatMessage;
    }

    public void sendChatMessage(String text){
        this.messageField.sendKeys(text);
        this.submitButton.click();
    }
}
