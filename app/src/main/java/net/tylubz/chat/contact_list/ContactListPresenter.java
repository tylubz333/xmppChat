package net.tylubz.chat.contact_list;


import net.tylubz.chat.contact_list.model.Contact;
import net.tylubz.chat.contact_list.model.Message;
import net.tylubz.chat.contact_list.services.XmppServiceTask;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import io.reactivex.Completable;
//import io.reactivex.schedulers.Schedulers;

/**
 * Implementation of dialog presenter interface
 *
 * @author Sergei Lebedev
 */

public class ContactListPresenter implements ContactListContract.Presenter {

    private final ContactListContract.View dialogView;

    List<Contact> contactList = new ArrayList<>();

    private final XmppServiceTask xmppServiceTask;

    public ContactListPresenter(final ContactListContract.View dialogView) {

        xmppServiceTask = new XmppServiceTask(msg -> dialogView.onMessageReceive(msg));
//        xmppServiceTask.execute();
        try {
            xmppServiceTask.establishConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.dialogView = dialogView;
        this.dialogView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        xmppServiceTask.closeConnection();
    }

    @Override
    public void sendMessage(Message message) {
        xmppServiceTask.sendMessage(message.getMessage());
    }

    @Override
    public void sendFile(File file) {
        xmppServiceTask.sendFile(file);
    }

    @Override
    public List<Contact> getContactList() {
//        Completable.fromAction(()-> xmppService.establishConnection())
//                .observeOn(Schedulers.io())
//                .doOnComplete(() -> { list = xmppService.getContactList();
//                    Log.i("mytag", list.toString());
//        });


//        return Completable.fromAction(()-> xmppServiceTask.establishConnection())
//                .observeOn(Schedulers.io()).toObservable().startWith(xmppServiceTask.get)
        return xmppServiceTask.getContactList();
    }
}