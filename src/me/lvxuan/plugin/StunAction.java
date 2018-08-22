package me.lvxuan.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

public class StunAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        //Get all the required data from data keys
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        if (null == editor) {
            return;
        }

        final Project project = e.getProject();
        //Access document, caret, and selection
        final Document document = editor.getDocument();
        final SelectionModel model = editor.getSelectionModel();

        final int start = model.getSelectionStart();
        final int end = model.getSelectionEnd();

        final String selectedText = model.getSelectedText();

        String tagText = selectedText;

        if (selectedText.contains(" ")) {
            tagText = space2Underline(selectedText);
        } else if (selectedText.contains("_")) {
            tagText = unDerline2Space(selectedText);
        }

        //Making the replacement
        String finalTagText = tagText;
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.replaceString(start, end, finalTagText)
        );
//        model.removeSelection();

    }

    private String space2Underline(String source) {
        source = source.replaceAll("\\s+", "_");
        return source.replaceAll("_+", "_");
    }

    private String unDerline2Space(String source) {
        source = source.replaceAll("_+", " ");
        return source.replaceAll("\\s+", " ");
    }


}
